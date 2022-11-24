package org.programs.kdt.RestController;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Exception.BusinessException;
import org.programs.kdt.Voucher.domain.VoucherCreateRequest;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.Voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/voucher")
@RequiredArgsConstructor
public class RestVoucherController {

    private final VoucherService voucherService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Voucher>> findVoucherList() {
        List<Voucher> voucherList = voucherService.findAll();
        return ResponseEntity.ok(voucherList);
    }

    @GetMapping(value = "/{voucherId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Voucher> findCustomer(@PathVariable UUID voucherId) {
        Optional<Voucher> voucherOptional = voucherService.findById(voucherId);
        if (voucherOptional.isPresent()) {
            return ResponseEntity.ok(voucherOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVoucher(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = voucherCreateRequest.toVoucher();
        voucherService.insert(voucher);
        return ResponseEntity.created(URI.create("/v1/voucher/" + voucher.getVoucherId())).build();
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID voucherId) {
        voucherService.delete(voucherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/time/{localDate}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Voucher>> findByLocalDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
        List<Voucher> voucherList = voucherService.findLocalDate(localDate);
        return ResponseEntity.ok(voucherList);
    }

    @GetMapping(value = "/type/{type}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Voucher>> findByVoucherType(@PathVariable String type) {
        try {
        VoucherType voucherType = VoucherType.findVoucherType(type);
        List<Voucher> voucherList = voucherService.findByType(voucherType);
        return ResponseEntity.ok(voucherList);
        } catch (BusinessException businessException) {
            return ResponseEntity.notFound().build();
        }
    }
}
