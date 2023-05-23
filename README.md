"# Persistence-Concepts"

In this repo we can explore different concepts of Persistence.

Testing git fetch and pull

Always keep in mind that there generally are at least three copies of a project on your workstation.

- One copy is your own repository with your own commit history (the already saved one, so to say).
- The second copy is your working copy where you are editing and building (not committed yet to your repo).
- The third copy is your local “cached” copy of a remote repository (probably the original from where you cloned yours).

git fetch: is the command that tells your local git to retrieve the latest meta-data info from the original (yet doesn’t do any file transferring. It’s more like just checking to see if there are any changes available).

git pull: on the other hand does that AND brings (copy) those changes from the remote repository.

- git status shows the diff between local and remote repos only once we do 'git fetch'. Before that local repo is not aware of remote changes.
- git branch --set-upstream-to origin/topic2 should be set to see the changes from remote when using git status command.

git checkout:

-

git branch:

- git branch <topic-branch> will create a new branch. It automatically copies the changes to the new branch.
- git switch switches the branch.
- Remeber, the changes will be saved to the topic whereever we commit and other branch will not have the un-commited chnages.
  Ex: topic2 created from topic1, topic2 imports all ongoing changes from topic1. Once we commit topic2 the changes will be saved into topic2 and topic1 will not have the changes.

-

git merge:

- git merge <main> # This command merges the main branch chnages to current branch(topic)

git rebase:

- what is ?
  
git switch
git checkout
