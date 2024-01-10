var quill = new Quill('#editor', {
    modules: {
        toolbar: [
            [{header: [1, 2, 3, 4, 5, false]}],
            ['bold', 'italic', 'underline'],
            ['image', 'code-block']
        ]
    },
    placeholder: 'Note content ...',
    theme: 'snow'
});

quill.on('text-change', function () {
    var content = quill.root.innerHTML;
    $('#contentInput').val(content);
});


var content = document.getElementById('contentInput');
var form = document.getElementById('resource-form');
form.onsubmit = function () {
    if (content === null) {
        return true;
    }

    content.value = quill.root.innerHTML;
    return true;
};

window.onload = function () {
    quill.root.innerHTML = content.value;
}

$('#isPublicJs').on('click', function () {
    var cb = $('#isPublicJs').is(':checked');
    $('#passwordJs').prop('disabled', cb);
});