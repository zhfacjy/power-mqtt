package power.emq.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 浩发 on 2019/2/6 22:17
 * 电表数据
 */
@Data
@Entity
@Table(name = "meter_record")
@EqualsAndHashCode(callSuper=false)
@EntityListeners(AuditingEntityListener.class)
public class MeterRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String centralNode;         // 中心节点
    private String meter;               // 电表
    private String command;             // 命令
    private String earlyWarning;        // 预警
    private Float ua;                   // a相电压
    private Float ub;
    private Float uc;
    private Float ia;                   // A相电流
    private Float ib;
    private Float ic;
    private Float pfa;                    //A相功率因数
    private Float pfb;
    private Float pfc;
    private Float frequency;            //频率
    private Double activePower;               // 总有功功率
    private Double electricEnergy;      // 电能
    private Integer temperature;        // 温度
    private Integer currentLimit;       // 电流上限
    private String crc;
    @CreatedDate
    private Timestamp createAt;         // 记录时间

    @Override
    public String toString() {
        return "MeterRecord{" +
                "id=" + id +
                ", centralNode='" + centralNode + '\'' +
                ", meter='" + meter + '\'' +
                ", command='" + command + '\'' +
                ", earlyWarning='" + earlyWarning + '\'' +
                ", va=" + ua +
                ", vb=" + ub +
                ", vc=" + uc +
                ", ia=" + ia +
                ", ib=" + ib +
                ", ic=" + ic +
                ", pfa=" + pfa+
                ", pfb=" + pfb +
                ", pfc=" + pfc +
                ", power=" + activePower +
                ", electricEnergy=" + electricEnergy +
                ", temperature=" + temperature +
                ", currentLimit=" + currentLimit +
                ", crc='" + crc + '\'' +
                '}';
    }
}
