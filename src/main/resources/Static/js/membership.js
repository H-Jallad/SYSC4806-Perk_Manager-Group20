// Get the #content div (the parent element in landingPage.html)
//let contentDiv = document.getElementById('content');

// Attach a click event listener to the #content div
contentDiv.addEventListener('click', function(event) {
    // Check if the clicked element is the #upvote-button or #downvote-button
    if (event.target.id.startsWith('remove-membership-')) {
        // Get the membershipId from the clicked button's id
        let membershipId = event.target.id.split('-').pop();

        // Send an AJAX request to go back to og vote value
        $.ajax({
            type: 'POST',
            url: '/removeMembership/' + membershipId,
            data: JSON.stringify('REMOVE'),
            contentType: 'application/json',
            success: function() {
                // Update the vote count on the page
                fetch('/myMemberships-content')
                    .then(response => response.text())
                    .then(content => {
                        document.querySelector('#content').innerHTML = content;
                    });
            }
        });
    }


    // Get the membershipId from the clicked button's id
    const membershipSelect = document.querySelector('select[name="membership-name"]');

    if (event.target.id.startsWith('add-membership')) {
        let selectedMembership = membershipSelect.options[membershipSelect.selectedIndex].value;

        // Send an AJAX request to go back to og vote value
        $.ajax({
            type: 'POST',
            url: '/addMembership/' + selectedMembership,
            data: JSON.stringify('ADD'),
            contentType: 'application/json',
            success: function () {
                // Update the vote count on the page
                fetch('/myMemberships-content')
                    .then(response => response.text())
                    .then(content => {
                        document.querySelector('#content').innerHTML = content;
                    });
            }
        });
    }
});

