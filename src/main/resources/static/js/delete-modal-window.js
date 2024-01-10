function setNoteId(noteId) {
    document.getElementById('encryptDecryptId').value = noteId;
}

function setTitle(isEncrypted) {
    if (isEncrypted) {
        document.getElementById('encryptDecryptModalTitle').innerText = 'Decrypt';
        document.getElementById('encryptDecryptSubmitBtn').innerText = 'Decrypt';
    } else {
        document.getElementById('encryptDecryptModalTitle').innerText = 'Encrypt';
        document.getElementById('encryptDecryptSubmitBtn').innerText = 'Encrypt';
    }
}