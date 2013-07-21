package net.archiloque.sukoku;

/**
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("You need to specify the grid as parameter");
        }
        System.out.println(new Grid(args[0]));
    }
}
