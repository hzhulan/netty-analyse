package com.fh.model.template;

public class TemplatePatternDemo {

    /**
     * 模板模式 简而言之就是 多态
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
