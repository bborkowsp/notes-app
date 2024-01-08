document.addEventListener('DOMContentLoaded', function () {
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
});


$('#isPublicJs').on('click', function () {
    var cb = $('#isPublicJs').is(':checked');
    $('#passwordJs').prop('disabled', cb);
});



