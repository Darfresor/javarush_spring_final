package com.javarush.hibernate_final.ostapenko.hibernate.repository.jdbc_template;

import com.javarush.hibernate_final.ostapenko.hibernate.entity.Role;
import com.javarush.hibernate_final.ostapenko.hibernate.mapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.javarush.hibernate_final.ostapenko.hibernate.repository.jdbc_template.RoleQueries.*;

@Repository
public class JdbcTemplateRoleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper;

    @Autowired
    public JdbcTemplateRoleRepository(JdbcTemplate jdbcTemplate, RoleRowMapper roleRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleRowMapper = roleRowMapper;
    }


    public Optional<Role> findById(Long id) {
        try {
            Role role = jdbcTemplate.queryForObject(FIND_BY_ID, roleRowMapper, id);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Role findByName(String name) {
        return jdbcTemplate.queryForObject(FIND_BY_NAME, roleRowMapper, name);
    }


    public List<Role> findAll() {
        return jdbcTemplate.query(FIND_ALL, roleRowMapper);
    }

    // save (создание или обновление)
    public Role save(Role role) {
        if (role.getId() == null) {
            jdbcTemplate.update(SAVE_STEP1_INSERT, role.getName());

            // Получаем сгенерированный ID
            Long generatedId = jdbcTemplate.queryForObject(
                    SAVE_STEP2_GET_LAST_INSERT_ID, Long.class);
            role.setId(generatedId);
        } else {
            // UPDATE
            jdbcTemplate.update(SAVE_STEP1_UPDATE, role.getName(), role.getId());
        }
        return role;
    }


    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }


    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(COUNT_BY_ID, Integer.class, id);
        return count != null && count > 0;
    }

    public long count() {
        return jdbcTemplate.queryForObject(COUNT_ALL, Long.class);
    }
}
