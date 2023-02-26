# Fiew rules to keep a clean gitlab repository

## Git / Gitlab

Every feature or bug fix have an issues & a branch fork from develop. Branch are name issuesId-DescriptionInFiewWord (e. g. 12-Contributing).
Feature/fix bug branches are always merge on develop. Then develop will be merge on master once per sprint.

Branches are merge by merge request. Merge request need to be review by at least 2 others maintainers of the project.

Commit messages are short & clear. Commit messages can have a longer description after. Commit message refer to there issues.

e. g.:
```md
[#12] Initialize CONTRIBUTING rules for code format & git use.
CONTRIBUTING.md also include example to make it easily understandable.
```

To keep a clean tree, use ```git pull --rebase``` or configure your git repository with ```git config --local pull.rebase true```


## Tests

Every new functions need to be tested (except for View part that can't be easily tested).
Test Files for ```src/main/.../ClassName.java``` need to be ```src/test/.../ClassNameTest.java```



## Java code formating / naming

Functions & classes should be format to respect global Java formating rules. (cf https://google.github.io/styleguide/javaguide.html or https://www.oracle.com/technetwork/java/codeconventions-150003.pdf)

e. g.:
```java
public class ClassName {
    private String var;
    public static final int GLOBAL_VAR=2;
    private static final String Z_RESET="z";

    // CONSTRUCTORS --------------------------------------------------------------
    public ClassName(String var) {
        this.var=var;
    }

    // GET SET -------------------------------------------------------------------
    public String getVar() {return var;}

    // FUNCTIONS -----------------------------------------------------------------
    functionY() {
        var+=System.currentTimeMillis();
    }
    protected functionZ() {
        var=Z_RESET;
    }

    // SUB-CLASS -----------------------------------------------------------------
    class SubclassName {}
}
```

## Code

Code need to respect the class diagram or maitainers need to discuss about changing the class diagram.

### Favorite way to write code for this project :

We should avoid using fix values in function.
e. g.:
```java
//OK
public void fct(int t[], int toAdd){
    for(int i=0; i<t.length; i++){
        t[i]+=toAdd;
    }
}
//NOT OK (to avoid)
public void fct(int i[]){
    for(int i=0; i<45; i++){
        t[i]+=3;
    }
}
```

We should avoid using return before the end of a function.
e. g.:
```java
//NOT OK (to avoid)
public boolean isPointInList(List<Point> list, Point toFind){
    for(Point p : list){
        if(toFind.equals(p)){
            return true;
        }
    }
    return false;
}
//OK (but doing more loop turn than needed.)
public boolean isPointInList(List<Point> list, Point toFind){
    boolean found = false;
    for(Point p : list){
        if(toFind.equals(p)){
            found = true;
            break;
        }
    }
    return found;
}
//OK (but more complicated)
public boolean isPointInList(List<Point> list, Point toFind){
    boolean found = false;
    Iterator<Point> iterator = list.iterator();
    while(iterator.hasNext() && !found){
        Point p = iterator.next();
        if(toFind.equals(p)){
            found = true;
        }
    }
    return found;
}
```
