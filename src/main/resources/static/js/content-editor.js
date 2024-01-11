var toolbarOptions = [
    [{'header': [1, 2, 3, 4, 5, 6, false]}],
    ['bold', 'italic', 'underline'],
    ['image']
];

var quill = new Quill('#editor', {
    modules: {
        toolbar: {
            container: toolbarOptions,
            handlers: {
                image: imageHandler
            }
        }
    },
    placeholder: 'Note content ...',
    theme: 'snow'
});

function imageHandler() {
    var range = this.quill.getSelection();
    var value = prompt('Provide the image url here.');
    if (value) {
        this.quill.insertEmbed(range.index, 'image', value, Quill.sources.USER);
    }
}

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



