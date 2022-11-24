package org.programs.kdt.Wallet;

import java.util.UUID;

public record WalletCreateRequest(UUID voucherId, UUID customerId) {

}
