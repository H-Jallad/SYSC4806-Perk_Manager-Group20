// Get the #content div (the parent element in landingPage.html)
//let contentDiv = document.getElementById('content');

// Attach a click event listener to the #content div
contentDiv.addEventListener('click', function(event) {

    // Get the membershipId from the clicked button's id
    const membershipSelect = document.querySelector('select[name="membership-name"]');

    // Check if the clicked element is the #upvote-button or #downvote-button
    if (event.target.id.startsWith('remove-membership-')) {
        // Get the membershipId from the clicked button's id
        let membershipId = event.target.id.split('-').pop();

        $.ajax({
            type: 'POST',
            url: '/removeMembership/' + membershipId,
            data: JSON.stringify('REMOVE'),
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

    else if (event.target.id.startsWith('view-perks-')) {
        // Get the membershipId from the clicked button's id
        let membershipId = event.target.id.split('-').pop();

        fetch(`/viewMembershipPerks/${membershipId}`)
            .then(response => response.text())
            .then(content => {
                document.querySelector('#content').innerHTML = content;
            });
    }

    else if (event.target.id.startsWith('add-membership')) {
        let selectedMembership = membershipSelect.options[membershipSelect.selectedIndex].value;

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

    else if (event.target.id.startsWith('add-perk-button-')) {
        event.preventDefault();
        let membershipId = event.target.id.split('-').pop();
        let membershipDropdown = document.getElementById("add-perk-dropdown-" + membershipId)
        let perkName = membershipDropdown.querySelector('#perk-name').value; //  getElementById("perk-name").value;
        let description = membershipDropdown.querySelector('#perk-description').value;
        let expirationDate = membershipDropdown.querySelector('#perk-expiration-date').value;
        let perkLocations = membershipDropdown.querySelector('#perk-locations').value;
        let perkTimes = membershipDropdown.querySelector('#perk-times').value;
        let perkProduct = membershipDropdown.querySelector('#perk-product').value;


        $.ajax({
            type: 'POST',
            url: '/addPerk',
            data: JSON.stringify({
                membershipId: membershipId,
                perkName: perkName,
                description: description,
                expirationDate: expirationDate,
                perkLocations: perkLocations,
                perkTimes: perkTimes,
                perkProduct: perkProduct
            }),
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







