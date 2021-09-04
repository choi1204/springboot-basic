package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev", "staging", "production"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(storage.get(voucherId));
    }

    @Override
    public Voucher create(Voucher voucher) throws IOException {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> list() {
        return storage.values().stream().toList();
    }
}