package ru.jpanda.diplom.normalizedb.service.analysis;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.Util.Constants;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Attribute;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.RelationSchema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexey on 20.04.2017.
 */
@Component
public class AnalysisDB {

    private RelationSchema relationSchema;

    public AnalysisDB() {
    }

    public RelationSchema analysis() {
        return getData(relationSchema);
    }

    public RelationSchema getRelationSchema() {
        return relationSchema;
    }

    public void setRelationSchema(RelationSchema relationSchema) {
        this.relationSchema = relationSchema;
    }

    private RelationSchema getData(RelationSchema relationSchema) {
        ArrayList<Attribute> attributes = relationSchema.getAttributes();
        List<List<String>> attributeData = relationSchema.getDataFromAnalysis();
        for (Attribute attribute : attributes) {
            if (checkAttributeOnAnalysis(attribute)) {
                List<String> strings = attributeData.get(attribute.getArrayIndex());
                Set<String> uniqueString = new HashSet<>(strings);
                double percentNotUniqueRow = (uniqueString.size() * 100) / strings.size();
                if (percentNotUniqueRow <= Constants.PERCENT_UNIQUE_ROWS_COLUMN_CRITICAL) {
                    attribute.setCritical(true);
                } else if (percentNotUniqueRow <= Constants.PERCENT_UNIQUE_ROWS_COLUMN_WARNING) {
                    attribute.setWarning(true);
                } else if (percentNotUniqueRow <= Constants.PERCENT_UNIQUE_ROWS_COLUMN_NORMAL) {
                    attribute.setNotice(true);
                } else if (percentNotUniqueRow > Constants.PERCENT_UNIQUE_ROWS_COLUMN_NORMAL) {
                    attribute.setNormal(true);
                }
            }else {
                attribute.setNormal(true);
            }
        }
        return relationSchema;
    }

    private boolean checkAttributeOnAnalysis(Attribute attribute) {
        if (attribute.isAutoIncrement() || attribute.getIsPrimaryKey() || attribute.getIsForeignKey()) {
            return false;
        }
        return true;
    }

}
