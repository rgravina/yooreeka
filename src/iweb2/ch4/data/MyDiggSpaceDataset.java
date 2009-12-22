package iweb2.ch4.data;

import iweb2.ch4.model.DataPoint;

public class MyDiggSpaceDataset {
    
    private DataPoint[] data;

    private boolean verbose = true;
    
    public MyDiggSpaceDataset(DataPoint[] data) {
        this.data = data;

        if( verbose ) {
            System.out.println("\nCreated " + this.getClass().getSimpleName() + 
                    " dataset with " + data.length + " items:\n");
            for(DataPoint item : data) {
                System.out.println(item.toShortString());
            }
        }
    }
    
    public DataPoint[] getData() {
        return data;
    }
}
