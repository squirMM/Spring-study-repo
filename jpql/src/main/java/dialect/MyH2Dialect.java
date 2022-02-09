package dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**H2Dialect의 실제코드를 참고해가며 만드는것
 * persistence.xml에 등록해줘야,,,
 * 이미 있는 기능이지만 이렇게 만들어서 써먹을 수 있다~ 라는걸 보여주기 위함!
 * */
public class MyH2Dialect extends H2Dialect {
    public MyH2Dialect() {
        registerFunction("group_concat",new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
