document.addEventListener('DOMContentLoaded', function () {
    var quill = new Quill('#content', {
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
});

$('#isPublicCheckbox').on('click', function () {
    var cb = $('#isPublicCheckbox').is(':checked');
    $('#customPassword').prop('disabled', cb);
});



