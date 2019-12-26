/// <reference path="Validation.ts" />
var Validation;
(function (Validation) {
    var lettersRegexp = /^[A-Za-z]+$/;
    var LettersOnlyValidator = /** @class */ (function () {
        function LettersOnlyValidator() {
        }
        LettersOnlyValidator.prototype.isAcceptable = function (s) {
            return lettersRegexp.test(s);
        };
        return LettersOnlyValidator;
    }());
    Validation.LettersOnlyValidator = LettersOnlyValidator;
})(Validation || (Validation = {}));
/// <reference path="./other/stu-namespace/Validation.ts" />
/// <reference path="./other/stu-namespace/LettersOnlyValidator.ts" />
// Some samples to try
var strs = ["Hello", "98052", "101"];
// Validators to use
var validators = {};
validators["Letters only"] = new Validation.LettersOnlyValidator();
// Show whether each string passed each validator
for (var _i = 0, strs_1 = strs; _i < strs_1.length; _i++) {
    var s = strs_1[_i];
    for (var name_1 in validators) {
        console.log("\"" + s + "\" - " + (validators[name_1].isAcceptable(s) ? "matches" : "does not match") + " " + name_1);
    }
}
