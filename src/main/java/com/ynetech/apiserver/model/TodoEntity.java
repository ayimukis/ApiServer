package com.ynetech.apiserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;  // 이 오브젝트의 아이디
    @Column
    private String userId;  // 이 오브젝트를 생성한 사용자의 아이디
    @Column
    private String title;   // 투두 타이틀(예: 운동하기)
    @Column
    private boolean done;   // true - todo를 완료한 경우(checked)
}
