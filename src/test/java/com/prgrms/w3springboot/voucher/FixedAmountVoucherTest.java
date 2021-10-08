package com.prgrms.w3springboot.voucher;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prgrms.w3springboot.order.Order;
import com.prgrms.w3springboot.order.OrderItem;

class FixedAmountVoucherTest {

	@DisplayName("고정 할인 바우처를 생성한다.")
	@Test
	void testCreateFixedVoucher() {
		var customerId = UUID.randomUUID();
		var orderItems = List.of(
			new OrderItem(UUID.randomUUID(), 100L, 1)
		);
		var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L, VoucherType.FIXED, LocalDateTime.now());
		var order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);

		assertThat(order.totalAmount()).isEqualTo(90L);
	}

	@DisplayName("유효한 고정 할인 금액 적용을 확인한다.")
	@Test
	void testDiscount() {
		long beforeDiscount = 1000L;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.PERCENT, LocalDateTime.now());

		long afterDiscount = voucher.discount(beforeDiscount);

		assertThat(afterDiscount).isEqualTo(900L);
	}

	@DisplayName("유효하지 않은 고정 할인 금액이 입력되면 예외를 발생한다.")
	@Test
	void testValidFixed() {
		assertAll(
			() -> assertThatIllegalArgumentException().isThrownBy(
				() -> new FixedAmountVoucher(UUID.randomUUID(), 0, VoucherType.FIXED, LocalDateTime.now())),
			() -> assertThatIllegalArgumentException().isThrownBy(
				() -> new FixedAmountVoucher(UUID.randomUUID(), -20, VoucherType.FIXED, LocalDateTime.now())),
			() -> assertThatIllegalArgumentException().isThrownBy(
				() -> new FixedAmountVoucher(UUID.randomUUID(), 10001, VoucherType.FIXED, LocalDateTime.now()))
		);
	}
}
