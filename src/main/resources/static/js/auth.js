// auth.js - функции для работы с JWT аутентификацией

// Константы
const TOKEN_KEY = 'jwtToken';
const API_AUTH_URL = '/api/auth/login';

/**
 * Выполняет вход в систему
 * @param {string} username - логин пользователя
 * @param {string} password - пароль пользователя
 * @returns {Promise} - промис с результатом
 */
async function login(username, password) {
    try {
        const response = await fetch(API_AUTH_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error('Ошибка аутентификации');
        }

        const data = await response.json();

        // Сохраняем токен в localStorage
        localStorage.setItem(TOKEN_KEY, data.token);

        return {
            success: true,
            token: data.token
        };
    } catch (error) {
        console.error('Login error:', error);
        return {
            success: false,
            error: error.message
        };
    }
}

/**
 * Выполняет выход из системы
 */
function logout() {
    // Удаляем токен из localStorage
    localStorage.removeItem(TOKEN_KEY);

    // Перенаправляем на страницу логина
    window.location.href = '/ui/login?logout=true';
}

/**
 * Проверяет, авторизован ли пользователь
 * @returns {boolean} - true если есть токен
 */
function isAuthenticated() {
    return localStorage.getItem(TOKEN_KEY) !== null;
}

/**
 * Получает токен из localStorage
 * @returns {string|null} - токен или null
 */
function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

/**
 * Добавляет заголовок Authorization к запросу
 * @param {Object} headers - исходные заголовки
 * @returns {Object} - обновленные заголовки
 */
function addAuthHeader(headers = {}) {
    const token = getToken();
    if (token) {
        return {
            ...headers,
            'Authorization': `Bearer ${token}`
        };
    }
    return headers;
}

/**
 * Выполняет защищенный запрос с токеном
 * @param {string} url - URL запроса
 * @param {Object} options - параметры запроса
 * @returns {Promise} - промис с результатом
 */
async function authFetch(url, options = {}) {
    const authHeaders = addAuthHeader(options.headers || {});

    const response = await fetch(url, {
        ...options,
        headers: authHeaders
    });

    // Если 401 - токен невалиден
    if (response.status === 401) {
        logout();
        throw new Error('Требуется повторная авторизация');
    }

    return response;
}

/**
 * Проверяет авторизацию при загрузке страницы
 */
function checkAuthOnPageLoad() {
    const currentPath = window.location.pathname;

    // Если на странице логина и уже авторизован - редирект на главную
    if (currentPath.includes('/ui/login') && isAuthenticated()) {
        window.location.href = '/ui/home';
        return;
    }

    // Если не на странице логина и не авторизован - редирект на логин
    // Исключение: публичные страницы
    const publicPages = ['/ui/login', '/', '/public'];
    const isPublicPage = publicPages.some(page => currentPath.includes(page));

    if (!isPublicPage && !isAuthenticated()) {
        window.location.href = '/ui/login';
    }
}

// Экспортируем функции для использования
window.Auth = {
    login,
    logout,
    isAuthenticated,
    getToken,
    addAuthHeader,
    authFetch,
    checkAuthOnPageLoad
};