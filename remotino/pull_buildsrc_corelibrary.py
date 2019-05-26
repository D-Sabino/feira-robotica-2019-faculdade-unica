from os import system

confirm = str(input("Pull buildsrc and corelibrary? make shure these folder don't exist in this directory [Y/N]")).lower()
if confirm == "y":
    system("git clone https://github.com/icarohs7/unox-buildsrc buildSrc")
    system("git clone https://github.com/icarohs7/unox-android-corelibrary corelibrary")
    print("done")
    system("pause")
