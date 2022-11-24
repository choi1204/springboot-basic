package org.programs.kdt.Controller;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherCreateRequest;
import org.programs.kdt.Voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/voucher")
@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping
    public String findVoucherList(Model model) {
        List<Voucher> voucherList = voucherService.findAll();
        model.addAttribute("voucherList", voucherList);

        return "voucherList";
    }

    @GetMapping("/{voucherId}")
    public String findVoucher(@PathVariable UUID voucherId, Model model) {
        Optional<Voucher> voucherOptional = voucherService.findById(voucherId);
        if (voucherOptional.isPresent()) {
            model.addAttribute("voucher", voucherOptional.get());
        }
        return "voucher";
    }

    @GetMapping("/create")
    public String createVoucherView(Model model) {
        return "createVoucher";
    }

    @PostMapping("/create")
    public String createVoucher(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = voucherCreateRequest.toVoucher();
        voucherService.insert(voucher);
        return "redirect:/voucher";
    }

    @PostMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.delete(voucherId);
        return "redirect:/voucher";
    }

}
