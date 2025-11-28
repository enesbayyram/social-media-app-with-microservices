class StringUtils {

    isNullOrEmpty(value: string): boolean {
        return value == null || value.length == 0;
    }


}

export default new StringUtils();