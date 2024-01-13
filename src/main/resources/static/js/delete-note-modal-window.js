function setNoteId(noteId) {
    document.getElementById('encryptDecryptId').value = noteId;
}

function setTitleAndVisibility(isEncrypted) {
    const titleElement = document.getElementById('encryptDecryptModalTitle');
    const submitBtnElement = document.getElementById('encryptDecryptSubmitBtn');
    const progressBarElement = document.getElementById('entropy-progress-bar');
    const entropyContainerElement = document.getElementById('entropy-container');
    const progressBarBodyTextElement = document.getElementById('progress-bar-body-text');

    if (isEncrypted) {
        titleElement.innerText = 'Decrypt';
        submitBtnElement.innerText = 'Decrypt';
        progressBarBodyTextElement.innerText = '';

        // Hide progress bar and entropy container
        progressBarElement.style.display = 'none';
        entropyContainerElement.style.display = 'none';
    } else {
        titleElement.innerText = 'Encrypt';
        submitBtnElement.innerText = 'Encrypt';
        progressBarBodyTextElement.innerText = 'Remember, encrypted note will not be visible to others.';

        // Show progress bar and entropy container
        progressBarElement.style.display = 'block';  // or 'flex' based on your styling
        entropyContainerElement.style.display = 'block';  // or 'flex' based on your styling
    }
}