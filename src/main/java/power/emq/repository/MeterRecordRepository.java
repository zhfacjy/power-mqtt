package power.emq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import power.emq.model.MeterRecord;

/**
 * 数据库表meter_record主要记录是电表发来的数据
 */
public interface MeterRecordRepository extends JpaRepository<MeterRecord, Integer> {
}
