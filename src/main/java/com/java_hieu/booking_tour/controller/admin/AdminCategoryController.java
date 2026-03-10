package com.java_hieu.booking_tour.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java_hieu.booking_tour.constant.MessageConstants;
import com.java_hieu.booking_tour.entity.Category;
import com.java_hieu.booking_tour.repository.projection.CategoryProjection;
import com.java_hieu.booking_tour.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public String index(Model model) {
    List<CategoryProjection> categories = categoryService.findAllWithTourCount();
    model.addAttribute("categories", categories);
    return "admin/category/index";
  }

  @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("category", new Category());
    return "admin/category/create";
  }

  @PostMapping("/create")
  public String store(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
    categoryService.create(category);
    redirectAttributes.addFlashAttribute("success", MessageConstants.Action.ADD_SUCCESS);
    return "redirect:/admin/categories";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Category category = categoryService.findById(id);
      model.addAttribute("category", category);
      return "admin/category/edit";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
      return "redirect:/admin/categories";
    }
  }

  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id,
                        @ModelAttribute Category category,
                        RedirectAttributes redirectAttributes) {
    try {
      categoryService.update(id, category);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.UPDATE_SUCCESS);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
    }
    return "redirect:/admin/categories";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Integer id,
                        RedirectAttributes redirectAttributes) {
    try {
      categoryService.delete(id);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.DELETE_SUCCESS);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
    }
    return "redirect:/admin/categories";
  }
}
