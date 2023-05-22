"# Persistence-Concepts"

In this repo we can explore different concepts of Persistence.

Testing git fetch and pull

git fetch: is the command that tells your local git to retrieve the latest meta-data info from the original (yet doesn’t do any file transferring. It’s more like just checking to see if there are any changes available).

git pull: on the other hand does that AND brings (copy) those changes from the remote repository. My Chnage Here for merge conf2

git checkout:

-

git branch:

- git branch <topic-branch> will create a new branch. It automatically copies the changes to the new branch.
- git switch switches the branch. Once we commit a newly creaed topic branch the changes will be saved and the old branch from where we creaed a topic branch and imported the changes will not have the changes.
  Ex: topic2 created from topic1, topic2 imports all ongoing changes from topic1. Once we commit topic2 the changes will be saved into topic2 and topic1 will not have the changes.

-
