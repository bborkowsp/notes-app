const stdCharsets = [
    {
        name: 'lowercase',
        re: /[a-z]/, // abcdefghijklmnopqrstuvwxyz
        length: 26,
    },
    {
        name: 'uppercase',
        re: /[A-Z]/, // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        length: 26,
    },
    {
        name: 'numbers',
        re: /[0-9]/, // 1234567890
        length: 10,
    },
    {
        name: 'symbols',
        re: /[^a-zA-Z0-9]/, // !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ (and any other)
        length: 33,
    },
];

// const calcCharsetLengthWith = (charsets) => (string) =>
//     charsets.reduce((length, charset) => length + (charset.re.test(string) ? charset.length : 0), 0);
//
// const calcCharsetLength = calcCharsetLengthWith(stdCharsets);
//
// const calculatePasswordEntropy = (string) => (string ? calcEntropy(calcCharsetLength(string), string.length) : 0);
function calculateEntropy(password) {
    var charsetSize = stdCharsets.reduce(function (length, charset) {
        return length + (charset.re.test(password) ? charset.length : 0);
    }, 0)
    return Math.round(password.length * Math.log(charsetSize) / Math.LN2);
}

document.addEventListener('DOMContentLoaded', function () {

    var entropyProgressBar = document.getElementById('entropy-progress-bar');
    var entropyText = document.getElementById('entropy-text');
    var passwordInput = document.getElementById('password');


    if (entropyProgressBar !== null && entropyText !== null && passwordInput !== null) {
        passwordInput.oninput = function () {
            var password = passwordInput.value;

            if (password.length === 0) {
                entropyProgressBar.style.width = '0%';
                entropyText.innerText = '';
                return;
            }

            var entropy = calculateEntropy(password);
            var percentage = Math.min(100, entropy);
            entropyProgressBar.style.width = percentage + '%';

            if (percentage < 70) {
                entropyProgressBar.classList.add('bg-danger');
                entropyProgressBar.classList.remove('bg-success');
                entropyText.innerText = 'Weak';
            } else {
                entropyProgressBar.classList.add('bg-success');
                entropyProgressBar.classList.remove('bg-danger');
                entropyText.innerText = 'Strong';
            }
        }
    }
});
