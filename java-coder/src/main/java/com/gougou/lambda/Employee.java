package com.gougou.lambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public
/**
 * @author fangxu
 * on date:2022/9/30
 */
class Employee {

    private int id;
    private String name;
    private int age;
    private double salary;

}
