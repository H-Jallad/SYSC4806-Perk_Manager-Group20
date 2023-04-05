// Get the #content div (the parent element in landingPage.html)
let contentDiv = document.getElementById('content');

// Attach a click event listener to the #content div
contentDiv.addEventListener('click', function(event) {
    // Check if the clicked element is the #upvote-button or #downvote-button
    if (event.target.id.startsWith('upvote-button-') || event.target.id.startsWith('downvote-button-')) {
        // Get the perkId from the clicked button's id
        let perkId = event.target.id.split('-').pop();

        // Get references to both buttons and vote count span using perkId
        let upvoteButton = document.getElementById('upvote-button-' + perkId);
        let downvoteButton = document.getElementById('downvote-button-' + perkId);
        let count = 1;
        let voteType = event.target.id.startsWith('upvote-button-') ? 'UPVOTE' : 'DOWNVOTE';
        let voteCount = document.getElementById('vote-count-' + perkId);

        // If the clicked button already has btn-success and is clicked again, remove the class
        if (event.target.classList.contains('btn-success') && event.target.dataset.clicked === 'true') {
            event.target.classList.remove('btn-success');
            event.target.dataset.clicked = 'false';

            // we want to do the opposite of the button clicked
            if (voteType == 'UPVOTE'){
                voteType = 'DOWNVOTE';
            } else {
                voteType = 'UPVOTE';
            }

            // Send an AJAX request to go back to og vote value
            $.ajax({
                type: 'POST',
                url: '/vote/1/' + perkId,
                data: JSON.stringify({ voteType }),
                contentType: 'application/json',
                success: function(data) {
                    // Update the vote count on the page
                    voteCount.innerHTML = data.useful;
                }
            });
        } else {
            // Toggle the color of the clicked button by adding/removing a class
            event.target.classList.add('btn-success');
            event.target.dataset.clicked = 'true';

            // If the other button has the btn-success class, remove it
            if (event.target.id.startsWith('upvote-button-') && downvoteButton.classList.contains('btn-success')) {
                downvoteButton.classList.remove('btn-success');
                downvoteButton.dataset.clicked = 'false';
                //undo the effects of the other button and increment
                count = 2;
            } else if (event.target.id.startsWith('downvote-button-') && upvoteButton.classList.contains('btn-success')) {
                upvoteButton.classList.remove('btn-success');
                upvoteButton.dataset.clicked = 'false';
                //undo the effects of the other button and decrement
                count = 2;
            }

            // Send an AJAX request to the server to update the vote count
            $.ajax({
                type: 'POST',
                url: '/vote/' + count + '/' + perkId,
                data: JSON.stringify({ voteType }),
                contentType: 'application/json',
                success: function(data) {
                    // Update the vote count on the page
                    voteCount.innerHTML = data.useful;
                }
            });

        }
    }
    else if (event.target.id.startsWith('search-perks')) {
        let searchText = document.getElementById('search-perk').value;
        if(searchText) {
            fetch(`/searchPerk/${searchText}`)
                .then(response => response.text())
                .then(content => {
                    document.querySelector('#content').innerHTML = content;
                });
        }
    }

    else if (event.target.id.startsWith('sort-usefulness')) {
        fetch("/sortPerk/usefulness")
            .then(response => response.text())
            .then(content => {
                document.querySelector('#content').innerHTML = content;
            });
    }

    else if (event.target.id.startsWith('sort-expiry-date')) {
        fetch("/sortPerk/expiryDate")
            .then(response => response.text())
            .then(content => {
                document.querySelector('#content').innerHTML = content;
            });
    }
});
