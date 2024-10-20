<script >
export default {
  beforeMount() {
      this.$alert('请重新登录', '登录过期', {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'warning',
            message: `请重新登录`
          });
          this.$router.push({ path: '/' });
        }
      });
  },

}

</script>

<template>

</template>

<style scoped lang="scss">

</style>