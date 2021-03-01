package com.fh.model.template;

public class TemplatePatternDemo {

    /**
     * 模板模式
     * @param args
     */
    public static void main(String[] args) {

        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
