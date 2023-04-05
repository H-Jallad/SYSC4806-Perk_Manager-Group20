# SYSC4806-Perk_Manager-Group20
![Build Status](https://github.com/H-Jallad/SYSC4806-Perk_Manager-Group20/actions/workflows/maven.yml/badge.svg)

#### Project Description:

The User can create a Profile with all the memberships and cards she has: Air Miles, CAA, credit card etc. A "Perk" is some kind of discount for a given membership and a given product: 10% off movies with a Visa card, free domestic flight with 10,000 Air Miles, etc. Perks may also have limitations in geographic area and in time. These perks can be posted anytime by any user (the user may first need to enter the product or membership for which the perk applies). Users can upvote or downvote these perks depending on whether they find them useful or incorrect. Search results for perks can be listed in decreasing amount of votes, or by expiry date. Logged-in users can search for products for which there are perks available matching their profile. A user without a profile (or not logged-in) can still search for perks, but won't get the benefit of finding matches for their memberships.

## Milestone 1: Early Prototype:

This program consists of a page to add a user, a page to add memberships, and a page to add perks to memberships. When the program is run, the landing consists of a button that adds a general user to the system and displays the user id. They can then press a link on that page to go back to add another user, or to go to the memberships page. If they select the link to go to the memberships page, the user will then be able to choose from a predetermined list of memberships via a dropdown list. The memberships they select represent memberships they own. They can then press a button to save their membership and dislay it. From here, they could click a link to add more memberships, click the link to view all their memberships, or click the link to allow them to add perks to memberships. If they click that link, they will be presented with two textboxes to add the perk name and description respectively, They will also see a dropdown list where they can seect the membership that the perk corresponds to. From here, the user can save their inputs via a save button, and then view the perk. From here, the user can click a link which would allow them to view all the perks they have and the memberships they correspond to.

## Milestone 2: Alpha Release:

This program now has a login functionality that can allow a user to either login and use the system through their account, or to use the system as a guest. In this way, multiple users can now use the system. The program also has a file-based persistence H2 database that persists user data across application restarts. The user interface of the application has been refreshed to encompass CSS Bootstrap and Javascript such that all functionality of the application can be accessed through the landing page. In this version of the application, a guest (not logged in) user can browse all the perks and upvote or downvote the perks they want. A logged in user can add memberships they have to their profile and add perks to the memberships. They can also upvote and downvote perks. They can then also view the perks associated with their specific memberships. Perks in this release have also been expanded to include an expiry date field and upvote/downvote buttons.

## Milestone 3: Final Release:

The program now has products, geographical locations, and valid times associated with perks. These are simply text fields that hold the information. The application can now perform searches for perks based on the perk name or the product associated with a perk. The user interface of the application has been updated to include sorting buttons that allow a user to sort the perks in the system by either by a descending amount of votes (usefulness) or by expiry date. The unit tests of the application have been thoroughly updated to include tests for negative and edge cases.

# UML Class diagram

![uml class diagram](https://user-images.githubusercontent.com/72241380/229987427-f4bb52f8-f561-4648-a22b-3652defc5d15.png)

## Database Schema
![ER diagram](https://user-images.githubusercontent.com/72241380/229987501-1811a1c2-1f69-42c8-b7fc-9e5f7e35fc71.png)

