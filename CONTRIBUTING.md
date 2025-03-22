**# Guide: How to Use Pull Requests for Code Contributions**

## **1. Clone the Repository (First-Time Setup)**
If you haven't cloned the project yet, run the following command:
```sh
 git clone https://github.com/DjalilX/Tourist_Guide_app.git
 cd Tourist_Guide_app
```
If you already have the project, make sure it’s updated:
```sh
 git pull origin main
```

---

## **2. Create a New Branch for Your Changes**
Never commit directly to `main`. Instead, create a new branch:
```sh
 git checkout -b feature-branch-name
```
Example:
```sh
 git checkout -b add-places-list
```

---

## **3. Make Changes & Commit**
After making your changes, check the status:
```sh
 git status
```
Stage the changes:
```sh
 git add .
```
Commit the changes with a meaningful message:
```sh
 git commit -m "Added new places list"
```

---

## **4. Push Your Branch to GitHub**
```sh
 git push origin feature-branch-name
```
Example:
```sh
 git push origin add-places-list
```

---

## **5. Open a Pull Request (PR)**
1. Go to the **GitHub repository**.
2. Click **Pull Requests** → **New Pull Request**.
3. Select **"Compare & pull request"** next to your branch.
4. Add a description of your changes.
5. Click **Create pull request**.

---

## **6. Code Review & Approval**
- The repository owner will review your PR.
- If everything is good, they will approve it.
- If changes are required, they will request modifications.

---

## **7. Merge & Delete the Branch**
Once the PR is approved and merged:
- Switch back to `main` and update your local branch:
```sh
 git checkout main
 git pull origin main
```
- Delete your feature branch:
```sh
 git branch -d feature-branch-name
 git push origin --delete feature-branch-name
```

---

Following this process ensures a smooth workflow where code is reviewed before being merged into `main`. Happy coding! 🚀

