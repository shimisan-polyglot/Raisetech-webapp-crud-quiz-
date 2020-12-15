package com.example.quizapp1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("page")
public class QuizAppController {
   private List<Quiz> quizzes = new ArrayList<>();
   private QuizFileDao quizFileDao = new QuizFileDao();
    private Object Quiz;

    @GetMapping("/quiz")
   public String quiz(Model model){
     int index = new Random().nextInt(quizzes.size());//この引数で指定した整数未満の数字をランダムに返す

     model.addAttribute("quiz",quizzes.get(index));
     return "quiz";
   }

    @GetMapping("/show")
    public String show(Model model) {
     model.addAttribute("quizzes",quizzes);
        return "list";
    }

    @PostMapping("/create")
    public String create(@RequestParam String question, @RequestParam boolean answer){
         Quiz quiz = new Quiz(question,answer);
         quizzes.add(quiz);

         return "redirect:/page/show";
    }

    @PostMapping("/page/delete")
    public String delete(@RequestParam String question, @RequestParam boolean answer){
        Quiz quiz = new Quiz(question,answer);
        quizzes.add(quiz);

        return "redirect:/page/show";
    }





    @GetMapping("/check")
    public String check(Model model,@RequestParam String question, @RequestParam boolean answer) {
// TODO: 回答が正しいかどうか
        for (Quiz quiz: quizzes) {
            if(quiz.getQuestion().equals(question)){
                model.addAttribute("quiz",quiz);
                if (quiz.isAnswer() == answer){
                    model.addAttribute("result","正解!");
                }else{
                    model.addAttribute("result","不正解!");
                }

        }

    }
        return"answer";
}
@PostMapping("/save")
public String save(RedirectAttributes attributes){
    try {
        quizFileDao.write(quizzes);
        attributes.addFlashAttribute("successMessage","ファイルに保存しました");
    } catch (IOException e) {
        e.printStackTrace();
        attributes.addFlashAttribute("errorMessage","ファイルの保存に失敗しました");
    }
    return "redirect:/page/show";
}




@GetMapping("/load")
public String load(RedirectAttributes attributes){
    try {
        quizzes = quizFileDao.read();
        attributes.addFlashAttribute("successMessage","ファイルに保存しました");
    } catch (IOException e) {
        e.printStackTrace();
        attributes.addFlashAttribute("errorMessage","ファイルの保存に失敗しました");
    }
    return "redirect:/page/show";
}
}
