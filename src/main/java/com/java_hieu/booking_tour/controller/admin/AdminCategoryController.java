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
import com.java_hieu.booking_tour.dto.admin.category.CategoryListResponse;
import com.java_hieu.booking_tour.entity.Category;
import com.java_hieu.booking_tour.exception.ResourceNotFoundException;
import com.java_hieu.booking_tour.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

  private final CategoryRepository categoryRepository;

  @GetMapping
  public String index(Model model) {
    List<CategoryListResponse> categories = categoryRepository.findAllWithTourCount();
    model.addAttribute("categories", categories);
    model.addAttribute("pageTitle", "<i class='bi bi-tags-fill me-2 text-danger'></i>Quản lý Danh mục");
    return "admin/category/index";
  }

  @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("category", new Category());
    model.addAttribute("pageTitle", "<i class='bi bi-plus-circle-fill me-2 text-danger'></i>Thêm danh mục mới");
    return "admin/category/create";
  }

  @PostMapping("/create")
  public String store(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
    categoryRepository.save(category);
    redirectAttributes.addFlashAttribute("success", MessageConstants.Action.ADD_SUCCESS);
    return "redirect:/admin/categories";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
      model.addAttribute("category", category);
      model.addAttribute("pageTitle", "<i class='bi bi-pencil-fill me-2 text-danger'></i>Sửa danh mục");
      return "admin/category/edit";
    } catch (ResourceNotFoundException e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
      return "redirect:/admin/categories";
    }
  }

  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id,
                        @ModelAttribute Category category,
                        RedirectAttributes redirectAttributes) {
    try {
      category.setId(id);
      categoryRepository.save(category);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.UPDATE_SUCCESS);
    } catch (ResourceNotFoundException e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
    }
    return "redirect:/admin/categories";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Integer id,
                        RedirectAttributes redirectAttributes) {
    try {
      categoryRepository.deleteById(id);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.DELETE_SUCCESS);
    } catch (ResourceNotFoundException e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
    }
    return "redirect:/admin/categories";
  }
}
