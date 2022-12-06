# Zuse Project

## Dev Steps - How to contribute to the project

! Make sure to run `` git pull `` in the develop branch before starting, to have the latest version.

Here again the steps for working:

1. Switch to develop branch: `` git checkout develop ``

2. Pull latest changes: `` git pull ``

3. Create new branch:  `` git checkout -b <my-new-branch> ``

4. Once finished with your work, commit your work

5. Then push to gitlab:  `` git push --set-upstream origin <my-new-branch> ``

6. Click on the generated link of the output, then create the Merge Request.

7. Make sure to add the reference to the Issue in the Merge request title for e.g. #12

8. Add a reviewer and submit

9. That's it


## Starting the project

1. Start a mysql database server with port 3306 and set root user password to "admin"

2. If Docker installed, you can use the docker-compose file and start up the container

3. Start the javaFX with your preferred IDE

4. That's it