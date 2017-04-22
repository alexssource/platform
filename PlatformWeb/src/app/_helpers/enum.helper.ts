export class EnumHelper {
    static getNamesAndValues<T extends number>(e: any) {
        return EnumHelper.getNames(e).map(n => ({ name: n, value: e[n] as T }));
    }


    static getNames(e: any) {
        return EnumHelper.getObjValues(e).filter(v => typeof v === "string") as string[];
    }


    private static getObjValues(e: any): (number | string)[] {
        return Object.keys(e).map(k => e[k]);
    }
}
