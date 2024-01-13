const charsets = [
    {name: 'lowercase', re: /[a-z]/, length: 26},
    {name: 'uppercase', re: /[A-Z]/, length: 26},
    {name: 'numbers', re: /[0-9]/, length: 10},
    {name: 'symbols', re: /[^a-zA-Z0-9]/, length: 33},
];

function calculateEntropy(password) {
    const charsetSize = charsets.reduce((length, charset) => length + (charset.re.test(password) ? charset.length : 0), 0);
    return Math.round(password.length * Math.log(charsetSize) / Math.LN2);
}

const entropyProgressBar = document.getElementById('entropy-progress-bar');
const passwordInput = document.getElementById('password');
const entropyInfo = document.getElementById('entropy-info');
const entropyContainer = document.getElementById('entropy-container');
entropyContainer.style.display = 'flex';

if (entropyProgressBar && passwordInput && entropyInfo) {
    passwordInput.oninput = function () {
        const password = passwordInput.value;

        if (password.length === 0) {
            entropyProgressBar.style.width = '0%';
            return;
        }

        const entropy = calculateEntropy(password);
        const percentage = Math.min(100, entropy);
        entropyProgressBar.style.width = percentage + '%';

        const setProgressBarClass = function (className, text) {
            entropyProgressBar.className = 'progress-bar ' + className;
            entropyInfo.innerText = 'Entropy: ' + entropy + ' bits';
        };

        if (percentage < 25) {
            setProgressBarClass('bg-danger', 'Weak');
        } else if (percentage < 50) {
            setProgressBarClass('bg-warning', 'Good');
        } else if (percentage < 75) {
            setProgressBarClass('bg-info', 'Strong');
        } else {
            setProgressBarClass('bg-success', 'Excellent');
        }
    };
}
