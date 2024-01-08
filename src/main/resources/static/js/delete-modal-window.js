$(document).ready(function () {
    $('#deleteModalWindow').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var noteId = button.data('noteid'); // Assuming you have a data-noteid attribute on the button

        // Update the "Delete note" button click event
        $('#deleteNoteButton').on('click', function () {
            // Perform the delete action here, using the noteId if needed
            // ...

            // Close the modal
            $('#deleteModalWindow').modal('hide');
        });
    });
});
