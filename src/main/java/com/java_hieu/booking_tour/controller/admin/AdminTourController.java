package com.java_hieu.booking_tour.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java_hieu.booking_tour.constant.MessageConstants;
import com.java_hieu.booking_tour.constant.WebConstants;
import com.java_hieu.booking_tour.entity.Tour;
import com.java_hieu.booking_tour.exception.BusinessException;
import com.java_hieu.booking_tour.exception.DuplicateResourceException;
import com.java_hieu.booking_tour.service.CategoryService;
import com.java_hieu.booking_tour.service.TourService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/tours")
@RequiredArgsConstructor
public class AdminTourController {

  private final TourService tourService;
  private final CategoryService categoryService;

  @GetMapping
  public String index(@RequestParam(defaultValue = "" + WebConstants.DEFAULT_PAGE) int page,
                      @RequestParam(defaultValue = "" + WebConstants.DEFAULT_PAGE_SIZE) int size,
                      Model model) {
    Page<Tour> tourPage = tourService.getPage(page, size);
    model.addAttribute("tourPage", tourPage);
    model.addAttribute("tours", tourPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", tourPage.getTotalPages());
    model.addAttribute("pageSize", size);
    return "admin/tour/index";
  }

 @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("tour", new Tour());
    model.addAttribute("categories", categoryService.findAll());
    return "admin/tour/create";
  }

  @PostMapping("/create")
  public String store(@Valid @ModelAttribute Tour tour, BindingResult bindingResult,
                      Model model, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("categories", categoryService.findAll());
      return "admin/tour/create";
    }
    try {
      tourService.create(tour);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.ADD_SUCCESS);
    } catch (DuplicateResourceException | BusinessException e) {
      model.addAttribute("categories", categoryService.findAll());
      model.addAttribute("error", e.getMessage());
      return "admin/tour/create";
    }
    return "redirect:/admin/tours";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Tour tour = tourService.findById(id);
      model.addAttribute("tour", tour);
      model.addAttribute("categories", categoryService.findAll());
      return "admin/tour/edit";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
      return "redirect:/admin/tours";
    }
  }

  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id,
                        @Valid @ModelAttribute Tour tour, BindingResult bindingResult,
                        Model model, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("categories", categoryService.findAll());
      return "admin/tour/edit";
    }
    try {
      tourService.update(id, tour);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.UPDATE_SUCCESS);
    } catch (DuplicateResourceException | BusinessException e) {
      model.addAttribute("categories", categoryService.findAll());
      model.addAttribute("error", e.getMessage());
      return "admin/tour/edit";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
    }
    return "redirect:/admin/tours";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Integer id,
                        RedirectAttributes redirectAttributes) {
    try {
      tourService.delete(id);
      redirectAttributes.addFlashAttribute("success", MessageConstants.Action.DELETE_SUCCESS);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", MessageConstants.Error.NOT_FOUND);
    }
    return "redirect:/admin/tours";
  }
}
