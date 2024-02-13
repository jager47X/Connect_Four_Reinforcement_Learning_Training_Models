package dto;

import java.util.Map;
import java.util.Set;

public class QTableExportDto {
    private Map<String, Set<QEntry>> exportingPolicyNetWork;

    public void setExportingPolicyNetWork(Map<String, Set<QEntry>> exportingPolicyNetWork) {


        this.exportingPolicyNetWork = exportingPolicyNetWork;
    }

    public Map<String, Set<QEntry>> getExportingPolicyNetWork() {
        return exportingPolicyNetWork;
    }


}
