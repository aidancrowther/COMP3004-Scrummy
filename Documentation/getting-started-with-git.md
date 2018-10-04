# Getting Started with Git
---

After cloning the repo, create your own branch: 

    git checkout -b <branch-name>

This command automatically creates and checks out the branch. In the future you omit the -b flag to checkout the branch.

    git checkout <branch-name>

This command can also be used to change what commit you are on

    git checkout <commit-id>

Then you can make your changes and commit them to your branch.

    git add .
    git commit

Once you're ready to add your changes to the stable master branch, then you can merge your branch into master.
To do so, first checkout master (if you are still on your branch):

    git checkout master

Then merge your branch into master:

    git merge <brach_name>

If there are conflicts in your merge you have to fix them before completing the merge. To fix them open the files that have conflics (their names will be printed out.) Look for the conflicting code, there will be symbols like '<<<<<HEAD' around the area. Once you have selected the code to keep, save the file and proceed with the merge by running 'git merge' again.

Once you have merged the new code into your local master you should push your changes to the remote so that everyone can have the updated copy of master. To do so run the push command:

    git push
    
Now the remote copy of master will match your local copy.

Everyone can now pull the changes into their own local master branch. To update your local copy make sure you have the master branch checked out, then pull the changes.

    git checkout master
    git pull
    
Alternatively, you can use git fetch to fetch all of the updates and all of the branches. You may also have to run git checkout HEAD to checkout the latest commit.

Now you should retire any branches that have been merged into master. To delete your old branch, go to the 'branches' tab on github and delete the branch. Run 'git fetch' on your local repo to delete it locally as well.

If you need to reset the remote link (since we've renamed the remote repo)

    git remote set-url origin <new-url>
