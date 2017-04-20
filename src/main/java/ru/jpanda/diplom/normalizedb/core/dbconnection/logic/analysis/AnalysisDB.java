package ru.jpanda.diplom.normalizedb.core.dbconnection.logic.analysis;

import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Attribute;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.RelationSchema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexey on 20.04.2017.
 */
public class AnalysisDB {

    private RelationSchema relationSchema;

    public AnalysisDB(RelationSchema relationSchema) {
        this.relationSchema = relationSchema;
    }

    public RelationSchema analysis() {
        return getData(relationSchema);
    }

    private RelationSchema getData(RelationSchema relationSchema) {
        ArrayList<Attribute> attributes = relationSchema.getAttributes();
        List<List<String>> attributeData = relationSchema.getData();
        for (Attribute attribute : attributes) {
            if(checkAttributeOnAnalysis(attribute)){
                List<String> strings = attributeData.get(attribute.getArrayIndex());
                Set<String> uniqueString = new HashSet<>(strings);
                int percentNotUniqueRow = (uniqueString.size()*100) / strings.size();
            }
        }
        return relationSchema;
    }

    private boolean checkAttributeOnAnalysis(Attribute attribute){
        if(attribute.isAutoIncrement() || attribute.getIsPrimaryKey() || attribute.getIsForeignKey()){
            return false;
        }
        return true;
    }

}
