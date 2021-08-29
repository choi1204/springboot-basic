package org.prgrms.orderapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.orderapp.model.FixedAmountVoucher;
import org.prgrms.orderapp.model.PercentDiscountVoucher;
import org.prgrms.orderapp.model.Voucher;
import org.prgrms.orderapp.repository.FileVoucherRepository;
import org.prgrms.orderapp.repository.MemoryVoucherRepository;
import org.prgrms.orderapp.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class VoucherServiceTest {

    @Configuration
    static class Config {
        @Bean
        public VoucherRepository voucherRepository() {
            return new MemoryVoucherRepository();
        }

        @Bean
        public VoucherService voucherService(VoucherRepository voucherRepository) {
            return new VoucherService(voucherRepository);
        }
    }
    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @Test
    @DisplayName("VoucherService는 FixedAmountVoucher 타입에 따라 Voucher를 생성할 수 있다.")
    void testCreateFixedAmountVoucher() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        Optional<Voucher> voucher = voucherService.createVoucher("fixed", "100");

        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getClass(), is(fixedAmountVoucher.getClass()));
        assertThat(voucher.get().getAmount(), is(fixedAmountVoucher.getAmount()));
    }

    @Test
    @DisplayName("VoucherService는 PercentDiscountVoucher 타입에 따라 Voucher를 생성할 수 있다.")
    void testCreatePercentDiscountVoucher() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        Optional<Voucher> voucher = voucherService.createVoucher("percent", "30");

        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getClass(), is(percentDiscountVoucher.getClass()));
        assertThat(voucher.get().getAmount(), is(percentDiscountVoucher.getAmount()));
    }

    @Test
    @DisplayName("VoucherService는 올바르지 않은 입력에는 Optional.empty를 리턴한다.")
    void testInvalidVoucher() {
        Optional<Voucher> voucher = voucherService.createVoucher("invalidType", "30");
        assertThat(voucher.isEmpty(), is(true));

        voucher = voucherService.createVoucher("fixed", "-10");
        assertThat(voucher.isEmpty(), is(true));

        voucher = voucherService.createVoucher("percent", "130");
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("VoucherService는 voucherId로 Voucher를 가져올 수 있다.")
    void testGetVoucher() {
        UUID id = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(id, 1000);
        voucherRepository.save(voucher);

        Voucher retrievedVoucher = voucherService.getVoucher(id);

        assertThat(retrievedVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("VoucherService는 존재하지 않는 voucherId로 조회시 에러를 발생시킨다.")
    void testGetVoucherWithInvalidId() {
        UUID id = UUID.randomUUID();

        assertThrows(RuntimeException.class, ()-> voucherService.getVoucher(id));
    }

    @Test
    @DisplayName("VoucherService는 바우처를 저장할 수 있습니다.")
    void testSaveVoucher() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.save(voucher);

        Voucher retrievedVoucher = voucherService.saveVoucher(voucher);

        assertThat(retrievedVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("VoucherService는 모든 바우처를 가져올 수 있습니다.")
    void testGetAllVoucher() {
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new PercentDiscountVoucher(UUID.randomUUID(), 30),
                new FixedAmountVoucher(UUID.randomUUID(), 400)
        );
        vouchers.forEach(voucher -> voucherRepository.save(voucher));

        List<Voucher> retrievedVouchers = voucherService.getAllVoucher();

        assertThat(retrievedVouchers, hasSize(vouchers.size()));
        assertThat(retrievedVouchers, containsInAnyOrder(vouchers.toArray()));
    }
}