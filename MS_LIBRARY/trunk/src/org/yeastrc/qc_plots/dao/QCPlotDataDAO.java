package org.yeastrc.qc_plots.dao;

import org.yeastrc.qc_plots.dto.QCPlotDataDTO;


/**
 * 
 * for table qc_plot_data
 */
public interface QCPlotDataDAO {

    public int save(QCPlotDataDTO qcPlotDataDTO);
    
    /**
     * @param experimentId
     * @param plotType
     * @return
     */
    public QCPlotDataDTO load(int experimentId, String plotType);
    
    /**
     * @param experimentId
     * @param plotType
     * @param dataVersion
     * @return
     */
    public QCPlotDataDTO loadFromExperimentIdPlotTypeAndDataVersion(int experimentId, String plotType, int dataVersion);
}
