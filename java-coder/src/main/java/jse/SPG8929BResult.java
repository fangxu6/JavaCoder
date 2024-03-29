package jse;

import java.util.ArrayList;
import java.util.List;

/**
 * className: SPG8929BBean
 * package: jse
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/11/10 14:54
 */
public class SPG8929BResult {


    Double VREF1_PRE;
    Double IB_PRE;
    Double ILIMIT_AVDD_PRE;
    Double ILIMIT_BST_PRE;
    Double FREQ_PRE;
    Double VBST_PRE;
    Double AVDD_PRE;
    Double AVEE_FREQ_PRE;
    Double AVEE_PRE;
    Double VOS_BST_PRE;


    public Double getVREF1_PRE() {
        return VREF1_PRE;
    }

    public void setVREF1_PRE(Double VREF1_PRE) {
        this.VREF1_PRE = VREF1_PRE;
    }

    public Double getIB_PRE() {
        return IB_PRE;
    }

    public void setIB_PRE(Double IB_PRE) {
        this.IB_PRE = IB_PRE;
    }

    public Double getILIMIT_AVDD_PRE() {
        return ILIMIT_AVDD_PRE;
    }

    public void setILIMIT_AVDD_PRE(Double ILIMIT_AVDD_PRE) {
        this.ILIMIT_AVDD_PRE = ILIMIT_AVDD_PRE;
    }

    public Double getILIMIT_BST_PRE() {
        return ILIMIT_BST_PRE;
    }

    public void setILIMIT_BST_PRE(Double ILIMIT_BST_PRE) {
        this.ILIMIT_BST_PRE = ILIMIT_BST_PRE;
    }

    public Double getFREQ_PRE() {
        return FREQ_PRE;
    }

    public void setFREQ_PRE(Double FREQ_PRE) {
        this.FREQ_PRE = FREQ_PRE;
    }

    public Double getVBST_PRE() {
        return VBST_PRE;
    }

    public void setVBST_PRE(Double VBST_PRE) {
        this.VBST_PRE = VBST_PRE;
    }

    public Double getAVDD_PRE() {
        return AVDD_PRE;
    }

    public void setAVDD_PRE(Double AVDD_PRE) {
        this.AVDD_PRE = AVDD_PRE;
    }

    public Double getAVEE_FREQ_PRE() {
        return AVEE_FREQ_PRE;
    }

    public void setAVEE_FREQ_PRE(Double AVEE_FREQ_PRE) {
        this.AVEE_FREQ_PRE = AVEE_FREQ_PRE;
    }

    public Double getAVEE_PRE() {
        return AVEE_PRE;
    }

    public void setAVEE_PRE(Double AVEE_PRE) {
        this.AVEE_PRE = AVEE_PRE;
    }

    public Double getVOS_BST_PRE() {
        return VOS_BST_PRE;
    }

    public void setVOS_BST_PRE(Double VOS_BST_PRE) {
        this.VOS_BST_PRE = VOS_BST_PRE;
    }

    public SPG8929BResult() {
        this.VREF1_PRE = 0.0;
        this.IB_PRE = 0.0;
        this.ILIMIT_AVDD_PRE = 0.0;
        this.ILIMIT_BST_PRE = 0.0;
        this.FREQ_PRE = 0.0;
        this.VBST_PRE = 0.0;
        this.AVDD_PRE = 0.0;
        this.AVEE_FREQ_PRE = 0.0;
        this.AVEE_PRE = 0.0;
        this.VOS_BST_PRE = 0.0;
    }

    public SPG8929BResult(Double definedNumber) {
        this.VREF1_PRE = definedNumber;
        this.IB_PRE = definedNumber;
        this.ILIMIT_AVDD_PRE = definedNumber;
        this.ILIMIT_BST_PRE = definedNumber;
        this.FREQ_PRE = definedNumber;
        this.VBST_PRE = definedNumber;
        this.AVDD_PRE = definedNumber;
        this.AVEE_FREQ_PRE = definedNumber;
        this.AVEE_PRE = definedNumber;
        this.VOS_BST_PRE = definedNumber;
    }

    public static List<SPG8929BResult> getSPG8929BResultList(List<SPG8929BBean> spg8929BBeanList) {
        List<SPG8929BResult> spg8929BResultList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            SPG8929BResult spg8929BResult = new SPG8929BResult();
            spg8929BResultList.add(spg8929BResult);
        }
        int num = 0;
        for (SPG8929BBean spg8929BBean : spg8929BBeanList) {
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setVREF1_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getVREF1_PRE() + spg8929BBean.VREF1_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setIB_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getIB_PRE() + spg8929BBean.IB_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setILIMIT_AVDD_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getILIMIT_AVDD_PRE() + Double.valueOf(spg8929BBean.ILIMIT_AVDD_PRE));
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setILIMIT_BST_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getILIMIT_BST_PRE() + Double.valueOf(spg8929BBean.ILIMIT_BST_PRE));
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setFREQ_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getFREQ_PRE() + spg8929BBean.FREQ_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setVBST_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getVBST_PRE() + spg8929BBean.VBST_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setAVDD_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getAVDD_PRE() + spg8929BBean.AVDD_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setAVEE_FREQ_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getAVEE_FREQ_PRE() + spg8929BBean.AVEE_FREQ_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setAVEE_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getAVEE_PRE() + spg8929BBean.AVEE_PRE);
            spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).setVOS_BST_PRE(spg8929BResultList.get(spg8929BBean.SITE_NUM - 1).getVOS_BST_PRE() + spg8929BBean.VOS_BST_PRE);
            num++;
        }

        for (int i = 0; i < spg8929BResultList.size(); i++) {
            spg8929BResultList.get(i).setVREF1_PRE(spg8929BResultList.get(i).getVREF1_PRE() / num);
            spg8929BResultList.get(i).setIB_PRE(spg8929BResultList.get(i).getIB_PRE() / num);
            spg8929BResultList.get(i).setILIMIT_AVDD_PRE(spg8929BResultList.get(i).getILIMIT_AVDD_PRE() / num);
            spg8929BResultList.get(i).setILIMIT_BST_PRE(spg8929BResultList.get(i).getILIMIT_BST_PRE() / num);
            spg8929BResultList.get(i).setFREQ_PRE(spg8929BResultList.get(i).getFREQ_PRE() / num);
            spg8929BResultList.get(i).setVBST_PRE(spg8929BResultList.get(i).getVBST_PRE() / num);
            spg8929BResultList.get(i).setAVDD_PRE(spg8929BResultList.get(i).getAVDD_PRE() / num);
            spg8929BResultList.get(i).setAVEE_FREQ_PRE(spg8929BResultList.get(i).getAVEE_FREQ_PRE() / num);
            spg8929BResultList.get(i).setAVEE_PRE(spg8929BResultList.get(i).getAVEE_PRE() / num);
            spg8929BResultList.get(i).setVOS_BST_PRE(spg8929BResultList.get(i).getVOS_BST_PRE() / num);
        }
        return spg8929BResultList;
    }

    public static SPG8929BResult getGap(List<SPG8929BResult> spg8929BResults) {
        SPG8929BResult spg8929BResult = new SPG8929BResult();
        SPG8929BResult minSPG8929BResult = new SPG8929BResult(Double.POSITIVE_INFINITY);
        SPG8929BResult maxSPG8929BResult = new SPG8929BResult(Double.NEGATIVE_INFINITY);
        for (SPG8929BResult spg : spg8929BResults) {
            if (spg.VREF1_PRE < minSPG8929BResult.VREF1_PRE) {
                minSPG8929BResult.VREF1_PRE = spg.VREF1_PRE;
            }
            if (spg.VREF1_PRE > maxSPG8929BResult.VREF1_PRE) {
                maxSPG8929BResult.VREF1_PRE = spg.VREF1_PRE;
            }
            if (spg.IB_PRE < minSPG8929BResult.IB_PRE) {
                minSPG8929BResult.IB_PRE = spg.IB_PRE;
            }
            if (spg.IB_PRE > maxSPG8929BResult.IB_PRE) {
                maxSPG8929BResult.IB_PRE = spg.IB_PRE;
            }
            if (spg.ILIMIT_AVDD_PRE < minSPG8929BResult.ILIMIT_AVDD_PRE) {
                minSPG8929BResult.ILIMIT_AVDD_PRE = spg.ILIMIT_AVDD_PRE;
            }
            if (spg.ILIMIT_AVDD_PRE > maxSPG8929BResult.ILIMIT_AVDD_PRE) {
                maxSPG8929BResult.ILIMIT_AVDD_PRE = spg.ILIMIT_AVDD_PRE;
            }
            if (spg.ILIMIT_BST_PRE < minSPG8929BResult.ILIMIT_BST_PRE) {
                minSPG8929BResult.ILIMIT_BST_PRE = spg.ILIMIT_BST_PRE;
            }
            if (spg.ILIMIT_BST_PRE > maxSPG8929BResult.ILIMIT_BST_PRE) {
                maxSPG8929BResult.ILIMIT_BST_PRE = spg.ILIMIT_BST_PRE;
            }
            if (spg.FREQ_PRE < minSPG8929BResult.FREQ_PRE) {
                minSPG8929BResult.FREQ_PRE = spg.FREQ_PRE;
            }
            if (spg.FREQ_PRE > maxSPG8929BResult.FREQ_PRE) {
                maxSPG8929BResult.FREQ_PRE = spg.FREQ_PRE;
            }
            if (spg.VBST_PRE < minSPG8929BResult.VBST_PRE) {
                minSPG8929BResult.VBST_PRE = spg.VBST_PRE;
            }
            if (spg.VBST_PRE > maxSPG8929BResult.VBST_PRE) {
                maxSPG8929BResult.VBST_PRE = spg.VBST_PRE;
            }
            if (spg.AVDD_PRE < minSPG8929BResult.AVDD_PRE) {
                minSPG8929BResult.AVDD_PRE = spg.AVDD_PRE;
            }
            if (spg.AVDD_PRE > maxSPG8929BResult.AVDD_PRE) {
                maxSPG8929BResult.AVDD_PRE = spg.AVDD_PRE;
            }
            if (spg.AVEE_FREQ_PRE < minSPG8929BResult.AVEE_FREQ_PRE) {
                minSPG8929BResult.AVEE_FREQ_PRE = spg.AVEE_FREQ_PRE;
            }
            if (spg.AVEE_FREQ_PRE > maxSPG8929BResult.AVEE_FREQ_PRE) {
                maxSPG8929BResult.AVEE_FREQ_PRE = spg.AVEE_FREQ_PRE;
            }
            if (spg.AVEE_PRE < minSPG8929BResult.AVEE_PRE) {
                minSPG8929BResult.AVEE_PRE = spg.AVEE_PRE;
            }
            if (spg.AVEE_PRE > maxSPG8929BResult.AVEE_PRE) {
                maxSPG8929BResult.AVEE_PRE = spg.AVEE_PRE;
            }
            if (spg.VOS_BST_PRE < minSPG8929BResult.VOS_BST_PRE) {
                minSPG8929BResult.VOS_BST_PRE = spg.VOS_BST_PRE;
            }
            if (spg.VOS_BST_PRE > maxSPG8929BResult.VOS_BST_PRE) {
                maxSPG8929BResult.VOS_BST_PRE = spg.VOS_BST_PRE;
            }
        }
        spg8929BResult.VREF1_PRE = maxSPG8929BResult.VREF1_PRE - minSPG8929BResult.VREF1_PRE;
        spg8929BResult.IB_PRE = maxSPG8929BResult.IB_PRE - minSPG8929BResult.IB_PRE;
        spg8929BResult.ILIMIT_AVDD_PRE = maxSPG8929BResult.ILIMIT_AVDD_PRE - minSPG8929BResult.ILIMIT_AVDD_PRE;
        spg8929BResult.ILIMIT_BST_PRE = maxSPG8929BResult.ILIMIT_BST_PRE - minSPG8929BResult.ILIMIT_BST_PRE;
        spg8929BResult.FREQ_PRE = maxSPG8929BResult.FREQ_PRE - minSPG8929BResult.FREQ_PRE;
        spg8929BResult.VBST_PRE = maxSPG8929BResult.VBST_PRE - minSPG8929BResult.VBST_PRE;
        spg8929BResult.AVDD_PRE = maxSPG8929BResult.AVDD_PRE - minSPG8929BResult.AVDD_PRE;
        spg8929BResult.AVEE_FREQ_PRE = maxSPG8929BResult.AVEE_FREQ_PRE - minSPG8929BResult.AVEE_FREQ_PRE;
        spg8929BResult.AVEE_PRE = maxSPG8929BResult.AVEE_PRE - minSPG8929BResult.AVEE_PRE;
        spg8929BResult.VOS_BST_PRE = maxSPG8929BResult.VOS_BST_PRE - minSPG8929BResult.VOS_BST_PRE;

        return spg8929BResult;
    }

}
