package com.prgrms.w3springboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
	private static final int DENOMINATOR_FOR_PERCENT = 100;
	private static final int MIN_PERCENT = 0;
	private static final int MAX_PERCENT = 100;

	private final UUID voucherId;
	private final long amount;
	private final VoucherType type;
	private final LocalDateTime createdAt;

	public PercentAmountVoucher(UUID voucherId, long amount, VoucherType type, LocalDateTime createdAt) {
		validate(amount);

		this.voucherId = voucherId;
		this.amount = amount;
		this.type = type;
		this.createdAt = createdAt;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long getAmount() {
		return amount;
	}

	@Override
	public VoucherType getVoucherType() {
		return VoucherType.PERCENT;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public long discount(long beforeDiscount) {
		long discountAmount = beforeDiscount - beforeDiscount * amount / DENOMINATOR_FOR_PERCENT;
		if (discountAmount < 0) {
			throw new IllegalArgumentException("잘못된 입력 범위입니다.");
		}
		return discountAmount;
	}

	@Override
	public void validate(long percent) {
		if (percent < MIN_PERCENT) {
			throw new IllegalArgumentException("유효한 범위보다 작습니다.");
		}

		if (percent == MIN_PERCENT) {
			throw new IllegalArgumentException("0을 가질 수 없습니다.");
		}

		if (percent > MAX_PERCENT) {
			throw new IllegalArgumentException("유효한 범위보다 큽니다.");
		}
	}

	@Override
	public String toString() {
		return "PercentAmountVoucher{" +
			"voucherId=" + voucherId +
			", amount=" + amount +
			'}';
	}
}
