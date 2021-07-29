package hillinsight.metadata.api.dto.web.req;

import java.io.Serializable;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2021/07/15
 */
public class Persion implements Serializable {

    private static final long serialVersionUID = 4359709211352400087L;
    public Long id;
    public String name;
    public final String userName;

    public Persion(Long id, String name){
        this.id = id;
        this.name = name;
        userName = "dddbbb";
    }

    public String toString() {
        return id.toString() + "--" + name.toString();
    }

}
