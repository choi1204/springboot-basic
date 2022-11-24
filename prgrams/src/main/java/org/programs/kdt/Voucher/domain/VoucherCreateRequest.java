package org.programs.kdt.Voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherCreateRequest(Long value,
                                   String voucherType) {

    public Voucher toVoucher() {
        VoucherType voucherTypeEnum = VoucherType.findVoucherType(this.voucherType);
        return voucherTypeEnum.createVoucher(UUID.randomUUID(), value, LocalDateTime.now());
    }
}
