package com.example.hrh.testweatherinfo.data.imoocInfo;

/**
 * Created by hrh on 2015/11/10.
 */
public class ImoocInfoDataBean {
    /**
     * status : 1
     */

    private String status;
    private ImoocInfoListDataBean[] data;


    public ImoocInfoListDataBean[] getImoocInfoListDataBean() {
        return data;
    }

    public void setImoocInfoListDataBean(ImoocInfoListDataBean[] imoocInfoListDataBean) {
        this.data = imoocInfoListDataBean;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ImoocInfoDataBean{" +
                "status='" + status + '\'' +
                ", imoocInfoListDataBean=" + data +
                '}';
    }
}
