package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;
import peaksoft.service.MenuItemService;

@RestController
@RequestMapping("/menuItems")
@RequiredArgsConstructor
public class MenuItemApi {
    private final MenuItemService menuItemService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/save")
    public SimpleResponse saveMenuItem(@RequestParam Long restaurantId,
                                       @RequestParam Long SubCategoryId,
                                       @RequestBody @Valid MenuItemRequest menuItemRequest){
        return menuItemService.saveMenu(restaurantId, SubCategoryId, menuItemRequest);
    }
    @PermitAll
    @GetMapping
    public PaginationMenuRes getPagination(@RequestParam int page, @RequestParam int size,
                                           @RequestParam(value = "isVegetarian", required = false) Boolean isVegetarian) {
        return menuItemService.getPagination(page, size,isVegetarian);
    }

    @PermitAll
    @GetMapping("/{id}")
    public MenuItemResponse getById(@PathVariable Long id) {
        return menuItemService.getById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse updateMenu(@PathVariable Long id, @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.updateMenu(id, menuItemRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteMenu(@PathVariable Long id) {
        return menuItemService.deleteMenu(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public PaginationMenuRes getAllMenuItems(@RequestParam String ascOrDesc,
                                                      @RequestParam int page,
                                                      @RequestParam  int size){
        return menuItemService.getAllMenuItems(ascOrDesc, page, size);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/search")
    public PaginationMenuRes search(@RequestParam String word,
                                              @RequestParam int page,
                                              @RequestParam  int size){
        return menuItemService.searchByName(word, page, size);
    }
}