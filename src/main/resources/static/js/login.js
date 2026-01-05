// login.js - JavaScript для страницы логина
console.log('🔐 login.js loaded');

// Функция для обработки входа
async function handleLogin() {
    console.log('🚀 handleLogin called');

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;

    console.log('👤 Username:', username ? 'filled' : 'empty');
    console.log('🔑 Password:', password ? 'filled' : 'empty');

    // Валидация
    if (!username || !password) {
        console.log('❌ Validation failed');
        showError('Заполните все поля');
        return;
    }

    console.log('🔄 Calling Auth.login...');

    // Проверяем, доступен ли Auth
    if (!window.Auth || typeof window.Auth.login !== 'function') {
        console.error('❌ Auth.login not available');
        showError('Системная ошибка. Обновите страницу.');
        return;
    }

    showLoading(true);
    hideAllMessages();

    try {
        const result = await window.Auth.login(username, password);
        console.log('📊 Auth.login result:', result);

        showLoading(false);

        if (result.success) {
            console.log('✅ Login successful');
            document.getElementById('successAlert').classList.remove('d-none');

            setTimeout(() => {
                window.location.href = '/ui/home';
            }, 1500);
        } else {
            console.log('❌ Login failed:', result.error);
            showError(result.error || 'Неправильный логин или пароль');
            document.getElementById('username').focus();
        }
    } catch (error) {
        console.error('💥 Login error:', error);
        showLoading(false);
        showError('Произошла ошибка при входе: ' + error.message);
    }
}

// Вспомогательные функции
function showError(message) {
    const errorAlert = document.getElementById('errorAlert');
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;
    errorAlert.classList.remove('d-none');
}

function showLoading(show) {
    const button = document.getElementById('loginButton');
    const spinner = document.getElementById('loadingSpinner');

    if (show) {
        button.disabled = true;
        button.textContent = 'Вход...';
        spinner.classList.remove('d-none');
    } else {
        button.disabled = false;
        button.textContent = 'Войти';
        spinner.classList.add('d-none');
    }
}

function hideAllMessages() {
    document.getElementById('errorAlert').classList.add('d-none');
    document.getElementById('successAlert').classList.add('d-none');
    document.getElementById('logoutAlert').classList.add('d-none');
}

// Инициализация страницы
function initLoginPage() {
    console.log('🛠️ Initializing login page');

    // Назначаем обработчик кнопке
    const loginButton = document.getElementById('loginButton');
    if (loginButton) {
        console.log('✅ Found login button');
        loginButton.addEventListener('click', handleLogin);
    } else {
        console.error('❌ Login button not found!');
    }

    // Обработка нажатия Enter
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                handleLogin();
            }
        });
    }

    // Обработка URL параметров
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('logout')) {
        document.getElementById('logoutAlert').classList.remove('d-none');
        localStorage.removeItem('jwtToken'); // Очищаем токен при logout
    }

    if (urlParams.has('error')) {
        showError('Ошибка аутентификации');
    }

    // Фокус на поле логина
    const usernameField = document.getElementById('username');
    if (usernameField) {
        usernameField.focus();
    }

    // Проверка авторизации
    if (window.Auth?.checkAuthOnPageLoad) {
        window.Auth.checkAuthOnPageLoad();
    }

    console.log('✅ Login page initialized');
}

// Экспортируем функции для глобального использования
window.LoginPage = {
    handleLogin,
    initLoginPage,
    showError,
    showLoading,
    hideAllMessages
};

// Запускаем инициализацию при загрузке DOM
document.addEventListener('DOMContentLoaded', function() {
    console.log('📄 DOM loaded - login page');

    // Проверяем, что мы на странице логина
    if (document.getElementById('loginForm')) {
        initLoginPage();
    } else {
        console.log('ℹ️ Not on login page, skipping login initialization');
    }
});

console.log('✅ login.js execution complete');