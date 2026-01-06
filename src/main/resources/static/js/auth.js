// auth.js - упрощенная версия для Cookie

console.log('🔐 Auth.js loaded (Cookie version)');

const COOKIE_NAME = 'jwtToken';

// Функция для получения токена из Cookie
function getTokenFromCookie() {
    const match = document.cookie.match(new RegExp('(^| )' + COOKIE_NAME + '=([^;]+)'));
    return match ? match[2] : null;
}

// Функция для проверки авторизации
function isAuthenticated() {
    return getTokenFromCookie() !== null;
}

// Login функция
async function login(username, password) {
    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                password: password
            }),
            credentials: 'include' // Важно для Cookie!
        });

        if (!response.ok) {
            throw new Error('Ошибка аутентификации');
        }

        const data = await response.json();
        console.log('✅ Login successful, token:', data.token.substring(0, 20) + '...');

        // Cookie уже установлена сервером
        // Можно сохранить и в localStorage для совместимости
        localStorage.setItem('jwtToken', data.token);

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

// Logout функция
function logout() {
    // Вызываем серверный logout
    fetch('/api/auth/logout', {
        method: 'POST',
        credentials: 'include'
    }).finally(() => {
        // Очищаем localStorage
        localStorage.removeItem('jwtToken');

        // Перенаправляем на страницу логина
        window.location.href = '/ui/login?logout=true';
    });
}

// Перехватчик для fetch запросов
(function() {
    const originalFetch = window.fetch;

    window.fetch = async function(...args) {
        const [url, options = {}] = args;

        // Проверяем, является ли URL публичным
        const isPublic = isPublicUrl(url);

        if (!isPublic) {
            // Получаем токен из Cookie
            const token = getTokenFromCookie();

            if (token) {
                const newOptions = {
                    ...options,
                    headers: {
                        ...options.headers,
                        'Authorization': `Bearer ${token}`
                    },
                    credentials: 'include'
                };

                const response = await originalFetch(url, newOptions);

                // Проверяем на 401
                if (response.status === 401) {
                    console.log('Token expired, redirecting to login');
                    window.location.href = '/ui/login?sessionExpired=true';
                    throw new Error('Session expired');
                }

                return response;
            }
        }

        // Для публичных URL или если нет токена
        return originalFetch(url, options);
    };

    function isPublicUrl(url) {
        const publicUrls = [
            '/api/auth/',
            '/ui/login',
            '/bootstrap/',
            '/jquery/',
            '/js/',
            '/css/',
            '/images/'
        ];
        return publicUrls.some(publicUrl => url.includes(publicUrl));
    }
})();

// Проверка авторизации при загрузке страницы
function checkAuthOnPageLoad() {
    const currentPath = window.location.pathname;
    const publicPages = ['/ui/login', '/ui/register', '/', '/public'];
    const isPublicPage = publicPages.some(page => currentPath.includes(page));

    // Если на странице логина и уже авторизован - редирект
    if (currentPath.includes('/ui/login') && isAuthenticated()) {
        window.location.href = '/ui/home';
        return;
    }

    // Если не публичная и не авторизован - на логин
    if (!isPublicPage && !isAuthenticated()) {
        window.location.href = '/ui/login';
    }
}

// Экспорт функций
window.Auth = {
    login,
    logout,
    isAuthenticated,
    getTokenFromCookie,
    checkAuthOnPageLoad
};

// Автопроверка при загрузке
document.addEventListener('DOMContentLoaded', function() {
    setTimeout(checkAuthOnPageLoad, 100);
});